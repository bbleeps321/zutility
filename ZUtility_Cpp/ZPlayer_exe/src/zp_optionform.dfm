object OptionForm: TOptionForm
  Left = 0
  Top = 0
  BorderStyle = bsDialog
  Caption = 'Options'
  ClientHeight = 158
  ClientWidth = 230
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -10
  Font.Name = 'Tahoma'
  Font.Style = []
  OldCreateOrder = False
  Position = poOwnerFormCenter
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 12
  object CancelButton: TButton
    Left = 104
    Top = 131
    Width = 60
    Height = 25
    Caption = 'Cancel'
    TabOrder = 0
    OnClick = CancelButtonClick
  end
  object OKButton: TButton
    Left = 169
    Top = 131
    Width = 61
    Height = 25
    Caption = 'OK'
    TabOrder = 1
    OnClick = OKButtonClick
  end
  object OptionsPageControl: TPageControl
    Left = 0
    Top = 0
    Width = 229
    Height = 127
    ActivePage = PlaySheet
    TabOrder = 2
    object PlaySheet: TTabSheet
      Caption = 'Play'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWhite
      Font.Height = -10
      Font.Name = 'Tahoma'
      Font.Style = []
      ParentFont = False
      object LoopCheck: TCheckBox
        Left = 2
        Top = 2
        Width = 43
        Height = 13
        Caption = 'Loop'
        Checked = True
        Color = clBtnFace
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        State = cbChecked
        TabOrder = 0
      end
      object ShuffleCheck: TCheckBox
        Left = 62
        Top = 2
        Width = 54
        Height = 13
        Caption = 'Shuffle'
        Color = clBtnFace
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        TabOrder = 1
      end
      object ReverseCheck: TCheckBox
        Left = 132
        Top = 2
        Width = 55
        Height = 13
        Caption = 'Reverse'
        Color = clBtnFace
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        TabOrder = 2
      end
      object AutoPlayGroup: TRadioGroup
        Left = 2
        Top = 20
        Width = 55
        Height = 59
        Caption = 'Autoplay'
        Color = clBtnFace
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ItemIndex = 1
        Items.Strings = (
          'None'
          'First'
          'All')
        ParentBackground = False
        ParentColor = False
        ParentFont = False
        TabOrder = 3
      end
      object HeadStartCheck: TCheckBox
        Left = 62
        Top = 31
        Width = 66
        Height = 13
        Caption = 'HeadStart'
        Color = clBtnFace
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        TabOrder = 4
        OnClick = HeadStartCheckClick
      end
      object HSGapIn: TEdit
        Left = 94
        Top = 48
        Width = 24
        Height = 20
        Enabled = False
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentFont = False
        TabOrder = 5
        Text = '0'
      end
      object HandicapCheck: TCheckBox
        Left = 132
        Top = 31
        Width = 66
        Height = 13
        Caption = 'Handicap'
        Color = clBtnFace
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        TabOrder = 6
        OnClick = HeadStartCheckClick
      end
      object HCGapIn: TEdit
        Left = 164
        Top = 48
        Width = 24
        Height = 20
        Enabled = False
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentFont = False
        TabOrder = 7
        Text = '0'
      end
    end
    object WindowSheet: TTabSheet
      Caption = 'Window'
      ImageIndex = 1
      object BGColorLabel: TLabel
        Left = 3
        Top = 21
        Width = 84
        Height = 12
        Alignment = taRightJustify
        Caption = 'Background Color'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentFont = False
        WordWrap = True
      end
      object TxtColorLabel: TLabel
        Left = 2
        Top = 56
        Width = 47
        Height = 12
        Caption = 'Text Color'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentFont = False
      end
      object OnTopCheck: TCheckBox
        Left = 2
        Top = 2
        Width = 85
        Height = 13
        Caption = 'Always on Top'
        Color = clBtnFace
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        TabOrder = 0
      end
      object BGColorBox: TColorBox
        Left = 2
        Top = 35
        Width = 125
        Height = 22
        Style = [cbStandardColors, cbExtendedColors]
        ItemHeight = 16
        TabOrder = 1
      end
      object TxtColorBox: TColorBox
        Left = 2
        Top = 73
        Width = 125
        Height = 22
        Selected = clWhite
        Style = [cbStandardColors, cbExtendedColors]
        ItemHeight = 16
        TabOrder = 2
      end
    end
    object FavSheet: TTabSheet
      Caption = 'Favorites'
      ImageIndex = 2
      object FavListBox: TListBox
        Left = 2
        Top = 2
        Width = 219
        Height = 77
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -10
        Font.Name = 'Tahoma'
        Font.Style = []
        ItemHeight = 12
        MultiSelect = True
        ParentFont = False
        ParentShowHint = False
        ShowHint = True
        TabOrder = 0
        OnMouseMove = FavListBoxMouseMove
      end
      object MoveUpButton: TButton
        Left = 158
        Top = 83
        Width = 63
        Height = 18
        Caption = 'Move Up'
        TabOrder = 1
        OnClick = MoveUp1Click
      end
      object MoveDownButton: TButton
        Left = 2
        Top = 83
        Width = 63
        Height = 18
        Caption = 'Move Down'
        TabOrder = 2
        OnClick = MoveDown1Click
      end
      object DeleteButton: TButton
        Left = 64
        Top = 83
        Width = 94
        Height = 18
        Caption = 'Remove Selected'
        TabOrder = 3
        OnClick = RemoveButtonClick
      end
    end
  end
  object FavListPopup: TPopupMenu
    Left = 112
    object MoveUp1: TMenuItem
      Caption = 'Move Up'
      ShortCut = 16422
      OnClick = MoveUp1Click
    end
    object MoveDown1: TMenuItem
      Caption = 'Move Down'
      ShortCut = 16424
      OnClick = MoveDown1Click
    end
    object MoveToTop1: TMenuItem
      Caption = 'Move To Top'
      ShortCut = 24614
      OnClick = MoveToTop1Click
    end
    object MoveToBottom1: TMenuItem
      Caption = 'Move To Bottom'
      ShortCut = 24616
      OnClick = MoveToBottom1Click
    end
    object RemoveSelected1: TMenuItem
      Caption = 'Remove Selected'
      ShortCut = 46
      OnClick = RemoveButtonClick
    end
  end
end